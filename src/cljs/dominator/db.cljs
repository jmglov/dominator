(ns dominator.db
  (:require [dominator.cards :as cards]))

(def default-db
  {:players []
   :current-player 0
   :supply cards/supply
   :trash cards/trash
   :game-started? false?})
