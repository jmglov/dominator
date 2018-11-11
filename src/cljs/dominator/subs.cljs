(ns dominator.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub ::players :players)
(rf/reg-sub ::current-player :current-player)
(rf/reg-sub ::supply :supply)
(rf/reg-sub ::trash :trash)
(rf/reg-sub ::game-started? :game-started?)
