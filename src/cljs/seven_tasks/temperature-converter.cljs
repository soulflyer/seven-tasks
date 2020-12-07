(ns seven-tasks.temperature-converter
  (:require
   [reagent.core :as r]
   [seven-tasks.routes :as routes]))

(defn c->f [centigrade]
  (+ 32 (* (/ 9 5) centigrade)))

(defn f->c [farenheit]
  (* (/ 5 9) (- farenheit 32)))

(defn page []
  (let [temperature-centigrade (r/atom 0)]
    (fn []
      [:span.main
       [:h1 "Task 2, Temperature Converter"]
       [:p "Convert Centigrade to Farenheit and vice versa"]
       [:input {:type "text"
                :value @temperature-centigrade
                :on-change #(reset! temperature-centigrade (-> % .-target .-value))}]
       [:p @temperature-centigrade " Centigrade"]
       [:p (c->f @temperature-centigrade) " Farenheit"]
       [:p [:a {:href (routes/path-for :index)} "Back to the list of tasks"]]])))
