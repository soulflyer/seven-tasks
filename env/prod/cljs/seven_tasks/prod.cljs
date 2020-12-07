(ns seven-tasks.prod
  (:require [seven-tasks.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
