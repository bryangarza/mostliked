(ns mostliked.core
  (:require [cheshire.core :refer :all]))

(defn -main []
  (let [likes (parsed-seq (clojure.java.io/reader "comments.hh.json") true)
        likes-merged (map (fn [[who n]]
                            (into {} [[:from_name who]
                                      [:like_count (apply + (map :like_count n))]]))
                          (group-by :from_name (doall (for [x likes]
                                                        (select-keys x [:from_name :like_count])))))
        likes-sorted (sort-by :like_count > likes-merged)
        likes-top (take 200 likes-sorted)]
    (doseq [line likes-top
            [k v] line]
      (if (number? v)
        (println v)
        (print (str v " "))))))
