(ns lcmap.rest.api.models.ccdc-piped-processes
  (:require [clojure.tools.logging :as log]
            [clojure.core.match :refer [match]]
            [compojure.core :refer [GET HEAD POST PUT context defroutes]]
            [ring.util.response :as ring]
            [lcmap.rest.api.jobs.ccdc-piped-processes :refer [get-result-path
                                                              get-job-result
                                                              result-table]]
            [lcmap.client.models.ccdc-piped-processes]
            [lcmap.client.status-codes :as status]
            [lcmap.rest.components.httpd :as httpd]
            [lcmap.rest.job.db :as db]
            [lcmap.rest.job.ccdc-pipe-runner :as ccdc-pipe-runner]
            [lcmap.rest.util :as util]))

;;; Supporting Constants ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def science-model-name "sample model")
(def result-keyspace "lcmap")

;;; Supporting Functions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn run-model [db eventd spectra x-val y-val start-time end-time
                           row col in-dir out-dir scene-list verbose]
  ;; generate job-id from hash of args
  ;; return status code 200 with body that has link to where the ccdc result will
  ;; be
  (log/debugf "run-model got args: %s" [spectra x-val y-val start-time end-time
                                        row col in-dir out-dir scene-list verbose])
  (let [job-id (util/get-args-hash science-model-name
                                   :spectra spectra
                                   :x-val x-val
                                   :y-val y-val
                                   :start-time start-time
                                   :end-time end-time
                                   :row row
                                   :col col
                                   :in-dir in-dir
                                   :out-dir out-dir
                                   :scene-list scene-list
                                   :verbose verbose)
        default-row {:science_model_name science-model-name
                     :result_keyspace result-keyspace
                     :result_table result-table
                     :result_id job-id
                     :status status/pending}]

    (ccdc-pipe-runner/run-model (:conn db)
                                  (:eventd eventd)
                                  job-id
                                  default-row
                                  result-table
                                  spectra x-val y-val start-time end-time
                                  row col in-dir out-dir scene-list verbose)
    (log/debug "Called ccdc-piped-processes runner ...")
    (ring/status
      (ring/response
        {:result
          {:link {:href (get-result-path job-id)}}})
      status/pending-link)))

;;; Routes ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defroutes routes
  (context lcmap.client.models.ccdc-piped-processes/context []
    (POST "/" [token spectra x-val y-val start-time end-time
                     row col in-dir out-dir scene-list verbose :as request]
      ;;(log/debug "Request data keys in routes:" (keys request))
      (run-model (httpd/jobdb-key request)
                 (httpd/eventd-key request)
                 spectra x-val y-val start-time end-time
                 row col in-dir out-dir scene-list verbose))
    (GET "/:job-id" [job-id :as request]
      (get-job-result (httpd/jobdb-key request) job-id))))

;;; Exception Handling ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; XXX TBD