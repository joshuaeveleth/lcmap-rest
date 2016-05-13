(ns lcmap.rest.api.jobs.ccdc
  (:require [clojure.tools.logging :as log]
            [clojure.core.match :refer [match]]
            [ring.util.response :as ring]
            [compojure.core :refer [GET HEAD POST PUT context defroutes]]
            [lcmap.client.jobs.ccdc]
            [lcmap.client.status-codes :as status]
            [lcmap.rest.api.jobs.core :as jobs]
            [lcmap.rest.components.httpd :as httpd]
            [lcmap.rest.middleware.http-util :as http]
            [lcmap.see.job.db :as db]))

;;; Supporting Constants ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def result-table "XXX")

;;; Routes ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defroutes routes
  (context lcmap.client.jobs.ccdc/context []
    (GET "/" request
      (jobs/get-resources (:uri request)))
    (GET "/:job-id" [job-id :as request]
      (jobs/get-job-result (httpd/jobdb-key request) result-table job-id))
    (PUT "/:job-id" [job-id :as request]
      (jobs/update-job (httpd/jobdb-key request) job-id))
    (HEAD "/:job-id" [job-id :as request]
      (jobs/get-info (httpd/jobdb-key request) job-id))
    (GET "/status/:job-id" [job-id :as request]
      (jobs/get-job-status (httpd/jobdb-key request) job-id))))
