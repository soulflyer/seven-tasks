(ns seven-tasks.flight-booker
  (:require [seven-tasks.routes :as routes]
            [reagent.core :as r]))

(defn page []
  (let [return-flight (r/atom true)]
    (let [outward-flight-date (r/atom "TODO todays date")
          return-flight-date  (r/atom "TODO tomorrows date")]
      (fn []
        [:span.main
         [:h1 "Task 3, Flight Booker"]
         [:p "return " [:input {:type "checkbox"
                                :name "return-button"
                                :checked @return-flight
                                :on-click #(swap! return-flight not)}]]
         [:p "outward flight date " [:input {:type "text"
                                             :value @outward-flight-date}]]
         (when @return-flight
           [:p "return flight date " [:input {:type "text"
                                              :value @return-flight-date}]])
         [:p
          [:a {:href (routes/path-for :index)} "Back to the list of tasks"]]]))))
