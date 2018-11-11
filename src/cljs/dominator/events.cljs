(ns dominator.events
  (:require [dominator.db :as db]
            [dominator.game :as game]
            [re-frame.core :as re-frame]
            [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::start-game
 (fn-traced [db _]
            (game/start 2 db)))
