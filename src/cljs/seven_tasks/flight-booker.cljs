(ns seven-tasks.flight-booker
  (:require
   [clojure.string :as str]
   [seven-tasks.routes :as routes]
   [reagent.core :as r]))

(defn date?
  "TEMPORARY FN, DEVELOPMENT ONLY: Check if input string looks like a
  date in yyyy:mm:dd format. This is only approximate, doesn't cover
  short months or leap years."
  [input-string]
  (let [date-vector (str/split input-string ":")
        year        (first  date-vector)
        month       (second date-vector)
        day         (last   date-vector)]
    (and
      (= 3 (count date-vector))
      (and (< 2019 year) (> 9999 year))
      (and (< 0 month)   (> 13 month))
      (and (< 0 day)     (> 31 day)))))

(defn before
  "TEMPORARY FN, DEVELOPMENT ONLY: Compares 2 date strings and returns
  true if the first is before the second."
  [first-date second-date]
  (let [first-date-number  (js/parseInt (str/join (str/split first-date ":")))
        second-date-number (js/parseInt (str/join (str/split second-date ":")))]
    (< first-date-number second-date-number)))

(defn page []
  (let [return-flight (r/atom true)]
    (let [outward-flight-date (r/atom "2020:12:09")
          outward-date-valid  (r/atom true)
          return-flight-date  (r/atom "2021:12:10")
          return-date-valid   (r/atom true)]
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
                                              :value @return-flight-date
                                              :on-change (fn [input-value]
                                                           (let [date-value (-> input-value .-target .-value)]
                                                             (reset! return-date-valid (date? date-value))
                                                             (reset! return-flight-date date-value)))
                                              :style {:color (if @return-date-valid "green" "red")}}]])
         (when (and @outward-date-valid
                    @return-date-valid
                    (before @outward-flight-date @return-flight-date))
           [:input {:type "button"
                    :value "Submit"
                    :on-click #(js/alert "Flights booked")}])
         [:p
          [:a {:href (routes/path-for :index)} "Back to the list of tasks"]]]))))
