(ns seven-tasks.home
  (:require [seven-tasks.routes :as routes]))

(defn page []
  (fn []
    [:span.main
     [:h1 "Seven-tasks"]
     [:ul
      [:li {:name "Click Counter" :key "task-1"}
       [:a {:href (routes/path-for :click-counter)}
        "Task 1, Click Counter"]]
      [:li {:name "Temperature Converter" :key "task-2"}
       [:a {:href (routes/path-for :temperature-converter)}
        "Task 2, Temperature Converter"]]
      [:li {:name "Flight Booker" :key "task-3"}
       [:a {:href (routes/path-for :flight-booker)}
        "Task 3, Flight Booker"]]
      (map (fn [task-id]
             [:li {:name (str "task-" task-id) :key (str "task-" task-id)}
              [:a {:href (routes/path-for :task {:task-id task-id})} "Task: " task-id]])
           (range 4 8))]]))
