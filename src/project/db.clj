(ns project.db

  (:require [clojure.java.jdbc :as jdbc]
            ))

;; (def db-spec

;;   {:classname "org.h2.Driver"

;;    :subprotocol "h2:mem"

;;    :subname "test-db"})



(def db-spec
    {:subprotocol "mysql"
     :subname "//127.0.0.1:3306/TESTDB1"
     :user "root"
     :password "Sathyabama@123"}
  )


(defn create-table []
  (println "create table func")
  (jdbc/execute! db-spec ["CREATE TABLE TEST_TABLE (ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255))"]))

(defn insert-data [name]
(println "insert data func")
(jdbc/with-db-connection [db-conn db-spec]

(jdbc/insert! db-conn

:TEST_TABLE

{:name name})))

(defn query-data []
(println "query data func")
(jdbc/with-db-connection [db-conn db-spec]

(jdbc/query db-conn ["SELECT * FROM TEST_TABLE"])))
