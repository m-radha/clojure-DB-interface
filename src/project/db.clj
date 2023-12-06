(ns project.db
  (:require [clojure.java.jdbc :as jdbc]))

(def db-spec
  {:subprotocol "mysql"
   :subname "//127.0.0.1:3306/TESTDB1"
   :user "root"
   :password "Sathyabama@123"})

(defn create-table []
  (jdbc/execute! db-spec ["CREATE TABLE IF NOT EXISTS TEST_TABLE (ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255))"]))

(defn insert-data [name]
  (jdbc/with-db-connection [db-conn db-spec]
    (jdbc/insert! db-conn :TEST_TABLE {:name name})))

(defn query-data []
  (jdbc/with-db-connection [db-conn db-spec]
    (jdbc/query db-conn ["SELECT * FROM TEST_TABLE"])))

(defn update-data [id name]
  (println "update data func")
  (jdbc/with-db-connection [db-conn db-spec]
    (jdbc/execute! db-conn ["UPDATE TEST_TABLE SET NAME=? WHERE ID=?" name id])))

(defn delete-data [id]
  (println "delete data func")
  (jdbc/with-db-connection [db-conn db-spec]
    (jdbc/execute! db-conn ["DELETE FROM TEST_TABLE WHERE ID=?" id])))
