;; (ns project.handler
;;   (:require [clojure.string :as str]
;;             [compojure.core :refer [defroutes GET POST]]
;;             [compojure.route :as route]
;;             [project.db :as db] 
;;             [ring.middleware.defaults :refer [site-defaults wrap-defaults]] 
;;             [ring.middleware.keyword-params :refer [wrap-keyword-params]]
;;             [ring.util.anti-forgery :refer [anti-forgery-field]]
;;             [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
;;             [ring.middleware.nested-params :refer [wrap-nested-params]]
;;             [ring.middleware.params :refer [wrap-params]]
;;             [ring.middleware.session :refer [wrap-session]] 
;;             [ring.util.response :as response]))


;; (defn home-page []

;;   (str "<h1>Welcome to Clojure Web DB App</h1>"
;;        "<a href='/insert'>Insert Data</a><br/>"
;;        "<a href='/data'>View Data</a>"))




;; (defroutes app-routes
;;   (GET "/" [] "Hello World")
;;   (GET "/home" [] (home-page))
;;   (GET "/insert" [] (str "<h2>Insert Data</h2>"
;;                          "<form action='/insert' method='post'>"
;;                          (anti-forgery-field)
;;                          "Name: <input type='text' name='name'><br/>"
;;                          "<input type='submit' value='Submit'></form>"))
;;   (POST "/insert" [params] (do (db/insert-data (:name params)) 
;;                                (response/redirect "/")))
;;   (route/not-found "Not Found"))

;; (GET "/data" [] (str "<h2>View Data</h2>"
;;                       (->> (db/query-data)
;;                            (map #(str "ID: " (:id %) ", Name: " (:name %)))
;;                            (str/join "<br/>"))))


;; (def app
;;   (-> app-routes
;;       (wrap-session)
;;       (wrap-params)
;;       (wrap-nested-params)
;;       (wrap-keyword-params)
;;       (wrap-defaults site-defaults)
;;       (wrap-anti-forgery)
;;       )) ; Add CSRF middleware appropriately



;; ;; (def app
;; ;;   (wrap-defaults app-routes site-defaults))


;; ;; (def app
;; ;;   (-> app-routes
;; ;;       (wrap-keyword-params)
;; ;;       (wrap-nested-params)
;; ;;       (wrap-params)
;; ;;       (wrap-session)
;; ;;       (wrap-defaults site-defaults)))



(ns project.handler
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [clojure.string :as str]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as response]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.nested-params :refer [wrap-nested-params]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.session :refer [wrap-session]]
            [project.db :as db]))




(defn home-page []
  (str "<h1>Welcome to Clojure Web DB App</h1>"
       "<a href='/insert'>Insert Data</a><br/>"
       "<a href='/data'>View Data</a>"))

(defroutes app-routes
  (GET "/" [] (home-page))
  (GET "/insert" [] (str "<h2>Insert Data</h2>"
                         "<form action='/insert' method='post'>"
                         (anti-forgery-field)
                         "Name: <input type='text' name='name'><br/>"
                         "<input type='submit' value='Submit'></form>")) 

  ;; (POST "/insert" [params] (do (db/insert-data (:name params))
  ;;                              (println params)
  ;;                              (response/redirect "/")))

  ;; (POST "/insert" [params]
  ;;   (let [form-name (:name params)] 
  ;;     (do
  ;;       (db/insert-data form-name) 
  ;;       (println params) 
  ;;       (response/redirect "/")))) 

  ;; (POST "/insert" [params]
  ;;   (let [form-name (:name params)]
  ;;     (db/insert-data form-name)
  ;;     (println "Data is likely inserted now")
  ;;     (response/redirect "/")))

  
 (POST "/insert" [name]
   (println name) 
   (db/insert-data name) 
   (response/redirect "/")) 

 
  (GET "/data" [] (str "<h2>View Data</h2>"
                       (->> (db/query-data)
                            (map #(str "ID: " (:id %) ", Name: " (:name %)))
                            (str/join "<br/>"))))
    (route/not-found "Not Found")
  )
;; (db/insert-data "aswini")


   (def app
  (-> app-routes
      wrap-params
      wrap-nested-params
      wrap-keyword-params
      wrap-session
      wrap-anti-forgery
      (wrap-defaults site-defaults)))
   
;; (def app
;;   (-> app-routes
;;       wrap-keyword-params
;;       wrap-nested-params
;;       wrap-params
;;       wrap-session
;;       wrap-anti-forgery
;;       (wrap-defaults site-defaults)))




