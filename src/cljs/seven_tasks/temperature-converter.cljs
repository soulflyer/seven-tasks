(ns seven-tasks.temperature-converter
  (:require
   [reagent.core :as r]
   [seven-tasks.routes :as routes]))

(defn c->f [centigrade]
  (+ 32 (* (/ 9 5) centigrade)))

(defn f->c [farenheit]
  (* (/ 5 9) (- farenheit 32)))

(defn page []
  (let [temperature-centigrade (r/atom 0)
        temperature-farenheit  (r/atom 32)]
    (fn []
      [:span.main
       [:h1 "Task 2, Temperature Converter"]
       [:p "Convert Centigrade to Farenheit and vice versa"]
       [:p
        [:input {:type "text"
                 :value @temperature-centigrade
                 :on-change (fn [input-value]
                              (let [degrees-centigrade (-> input-value .-target .-value)]
                                ((reset! temperature-centigrade degrees-centigrade)
                                 (reset! temperature-farenheit (c->f degrees-centigrade)))))}]
        " Centigrade "]
       [:p
        [:input {:type "text"
                 :value @temperature-farenheit
                 :on-change (fn [input-value]
                              (let [degrees-farenheit (-> input-value .-target .-value)]
                                (reset! temperature-farenheit degrees-farenheit)
                                (reset! temperature-centigrade (f->c degrees-farenheit))))}]
        " Farenheit"]
       [:p
        [:a {:href (routes/path-for :index)} "Back to the list of tasks"]]])))
