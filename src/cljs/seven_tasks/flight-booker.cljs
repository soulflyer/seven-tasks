(ns seven-tasks.flight-booker
  (:require
   [clojure.string :as str]
   [seven-tasks.routes :as routes]
   [reagent.core :as r]))

(defn date?
  "Check if input string looks like a date in yyyy:mm:dd format. This
  is only approximate, doesn't cover short months or leap years."
  [input-string]
  (let [date-vector (str/split input-string ":")
        year        (first  date-vector)
        month       (second date-vector)
        day         (last   date-vector)
        - (println "Year " year)
        - (println "Month " month)
        - (println "Day " day)]
    (and
      (= 3 (count date-vector))
      (and (< 2019 year) (> 9999 year))
      (and (< 0 month)   (> 13 month))
      (and (< 0 day)     (> 31 day)))))

(defn page []
  (let [return-flight (r/atom true)]
    (let [outward-flight-date (r/atom "2020:12:09")
          outward-date-valid  (r/atom true)
          return-flight-date  (r/atom "TODO tomorrows date")]
      (fn []
        [:span.main
         [:h1 "Task 3, Flight Booker"]
         [:p "return " [:input {:type "checkbox"
                                :name "return-button"
                                :checked @return-flight
                                :on-click #(swap! return-flight not)}]]
         [:p "outward flight date " [:input {:type "text"
                                             :value @outward-flight-date
                                             :on-change (fn [input-value]
                                                          (let [date-value (-> input-value .-target .-value)]
                                                            (reset! outward-date-valid (date? date-value))
                                                            (reset! outward-flight-date date-value)))
                                             :style {:color (if @outward-date-valid "green" "red")}}]]
         (when @return-flight
           [:p "return flight date " [:input {:type "text"
                                              :value @return-flight-date}]])
         [:p
          [:a {:href (routes/path-for :index)} "Back to the list of tasks"]]]))))
