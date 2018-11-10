(ns dominator.views
  (:require [re-frame.core :as re-frame]
            [dominator.subs :as subs]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Dominator"]
     [:h2 "Player 1"]
     [:h2 "Supply"]
     [:h2 "Trash"]
     [:h2 "Player 2"]]))
