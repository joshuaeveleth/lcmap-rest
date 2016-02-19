(ns lcmap.rest.api.models
  (:require [clojure.tools.logging :as log]
            [compojure.core :refer [GET HEAD POST PUT context defroutes]]
            [dire.core :refer [with-handler!]]
            [ring.util.response :as ring]
            [lcmap.client.models]
            [lcmap.client.status-codes :as status]
            [lcmap.rest.api.models.ccdc]
            [lcmap.rest.api.models.ccdc-docker-process]
            [lcmap.rest.api.models.ccdc-piped-processes]
            [lcmap.rest.api.models.sample-docker-process]
            [lcmap.rest.api.models.sample-os-process]
            [lcmap.rest.api.models.sample-piped-processes]))

;;; Supporting Functions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-resources [request]
  (ring/status
    (ring/response "models resources tbd")
    status/ok))

;;; Routes ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defroutes routes
  (context lcmap.client.models/context []
    (GET "/" request
      (get-resources (:uri request))))
  lcmap.rest.api.models.ccdc/routes
  lcmap.rest.api.models.ccdc-docker-process/routes
  lcmap.rest.api.models.ccdc-piped-processes/routes
  lcmap.rest.api.models.sample-docker-process/routes
  lcmap.rest.api.models.sample-os-process/routes
  lcmap.rest.api.models.sample-piped-processes/routes)

;;; Exception Handling ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; XXX TBD
