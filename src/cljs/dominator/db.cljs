(ns dominator.db
  (:require [dominator.cards :as cards]))

(def default-db
  {:players []
   :supply cards/supply
   :trash cards/trash
   :num-players 0
   :game-started? false?
   :current-player -1
   :turn-phase nil})
