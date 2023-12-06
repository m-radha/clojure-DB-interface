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
       "<a href='/data'>View Data</a><br/>"
      ;;  "<a href='/delete/56'>Delete Data</a><br/>"
      ;;  "<a href='/update/57'>Update Data</a><br/>" 
       "<a href='/update'>update Data</a><br/><br/>"
       "<a href='/delete'>Delete Data</a><br/>"
       ))

;; (defn delete-form [id]
;;   (str "<h2>Delete Data</h2>"
;;        "<form action=\"/delete/" id "\" method=\"post\">"
;;        (anti-forgery-field)
;;        "<input type=\"submit\" value=\"Confirm Delete\"></form>"))

;; (defn update-form [id]
;;   (str "<h2>Update Data</h2>"
;;        "<form action=\"/update/" id "\" method=\"post\">"
;;        (anti-forgery-field)
;;        "New name: <input type=\"text\" name=\"name\"><br/>"
;;        "<input type=\"submit\" value=\"Update\"></form>"))

(defroutes app-routes
  (GET "/" [] (home-page))
  (GET "/insert" [] (str "<h2>Insert Data</h2>"
                         "<form action='/insert' method='post'>" 
                         (anti-forgery-field)
                         "Name: <input type='text' name='name'><br/>"
                         "<input type='submit' value='Submit'></form>"))

  (POST "/insert" [name]
    (db/insert-data name)
    (response/redirect "/"))
  

  (GET "/update" [] (str "<h2>Update Data</h2>"
                         "<form action='/update' method='post'>"
                         (anti-forgery-field)
                         "ID: <input type='text' name='id'><br/>"
                         "New Name: <input type='text' name='new_name'><br/>"
                         "<input type='submit' value='Update'></form>"))
  
  (POST "/update" [id new_name]
    (db/update-data id new_name)
    (response/redirect "/"))

  (GET "/delete" [] (str "<h2>Delete Data</h2>"
                         "<form action='/delete' method='post'>"
                         (anti-forgery-field)
                         "ID: <input type='text' name='id'><br/>"
                         "<input type='submit' value='Delete'></form>"))
  
  (POST "/delete" [id]
    (db/delete-data id)
    (response/redirect "/"))



  (GET "/data" []
    (str "<h2>View Data</h2>"
         (->> (db/query-data)
              (map #(str "ID: " (:id %) ", Name: " (:name %)))
              (str/join "<br/>"))))

;;  (GET "/delete/:id" [id] (delete-form id))
;;  (POST "/delete/:id" [id]
;;    (db/delete-data (Integer/parseInt id))
;;    (response/redirect "/"))
 
;;  (GET "/update/:id" [id] (update-form id))
;;  (POST "/update/:id" [id name]
;;    (db/update-data (Integer/parseInt id) name)
;;    (response/redirect "/"))

  (route/not-found "Not Found"))



(def app
  (-> app-routes
      wrap-params
      wrap-nested-params
      wrap-keyword-params
      wrap-session
      wrap-anti-forgery
      (wrap-defaults site-defaults)))
