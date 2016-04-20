(ns lcmap.rest.types-test
  (:require [clojure.test :refer :all]
            [schema.core :as schema]
            [lcmap.rest.types :as types]))

(deftest predicates-positive-test
  (is (types/matches? #"\s" " "))
  (is (types/range? 1 10 1))
  (is (types/range? 1 10 5))
  (is (types/range? 1 10 10))
  (is (types/range? 1 10 "1"))
  (is (types/range? 1 10 "5"))
  (is (types/range? 1 10 "10"))
  (is (types/in-ints? 1 10 1))
  (is (types/in-ints? 1 10 5))
  (is (types/in-ints? 1 10 10))
  (is (types/in-ints? 1 10 "1"))
  (is (types/in-ints? 1 10 "5"))
  (is (types/in-ints? 1 10 "10"))
  (is (types/string-int? "0"))
  (is (types/string-int? "1"))
  (is (types/string-int? "10"))
  (is (types/string-int? "10000000"))
  (is (types/string-int? "-0"))
  (is (types/string-int? "-1"))
  (is (types/string-int? "-10"))
  (is (types/string-int? "-10000000"))
  (is (types/string-int? "+0"))
  (is (types/string-int? "+1"))
  (is (types/string-int? "+10"))
  (is (types/string-int? "+10000000"))
  (is (types/day-range? 1))
  (is (types/day-range? 15))
  (is (types/day-range? 31))
  (is (types/day-range? "1"))
  (is (types/day-range? "15"))
  (is (types/day-range? "31"))
  (is (types/month-range? 1))
  (is (types/month-range? 6))
  (is (types/month-range? 12))
  (is (types/month-range? "1"))
  (is (types/month-range? "6"))
  (is (types/month-range? "12"))
  (is (types/string-day? "1"))
  (is (types/string-day? "15"))
  (is (types/string-day? "31"))
  (is (types/string-month? "1"))
  (is (types/string-month? "6"))
  (is (types/string-month? "12"))
  (is (types/four-digit? "0000"))
  (is (types/four-digit? "1111"))
  (is (types/four-digit? "1984"))
  (is (types/four-digit? "2001"))
  (is (types/four-digit? "9999"))
  (is (types/string-year? "0000"))
  (is (types/string-year? "1111"))
  (is (types/string-year? "1984"))
  (is (types/string-year? "2001"))
  (is (types/string-year? "9999"))
  (is (types/string-date? "0000-01-01"))
  (is (types/string-date? "1984/04/15"))
  (is (types/string-date? "9999.12.31"))
  (is (types/string-date? "19840415"))
  (is (types/string-date? "1984.:.04.:|:.15"))
  (is (types/string-bool? "true"))
  (is (types/string-bool? "TRUE"))
  (is (types/string-bool? "false"))
  (is (types/string-bool? "FALSE")))

(deftest predicates-negative-test
  (is (not (types/matches? #"\s" "")))
  (is (not (types/range? 1 10 0)))
  (is (not (types/range? 1 10 11)))
  (is (not (types/range? 1 10 "0")))
  (is (not (types/range? 1 10 "11")))
  (is (not (types/in-ints? 1 10 0)))
  (is (not (types/in-ints? 1 10 11)))
  (is (not (types/in-ints? 1 10 "0")))
  (is (not (types/in-ints? 1 10 "11")))
  (is (not (types/string-int? "0.0")))
  (is (not (types/string-int? "1.0")))
  (is (not (types/string-int? "3.14159")))
  (is (not (types/string-int? "10000000.0")))
  (is (not (types/string-int? "a")))
  (is (not (types/day-range? 0)))
  (is (not (types/day-range? 32)))
  (is (not (types/day-range? 99)))
  (is (not (types/day-range? "0")))
  (is (not (types/day-range? "32")))
  (is (not (types/day-range? "-99")))
  (is (not (types/month-range? 0)))
  (is (not (types/month-range? 13)))
  (is (not (types/month-range? -1)))
  (is (not (types/month-range? "0")))
  (is (not (types/month-range? "13")))
  (is (not (types/month-range? "-1")))
  (is (not (types/string-day? "0")))
  (is (not (types/string-day? "32")))
  (is (not (types/string-day? "-99")))
  (is (not (types/string-month? "0")))
  (is (not (types/string-month? "13")))
  (is (not (types/string-month? "-1")))
  (is (not (types/four-digit? "00000")))
  (is (not (types/four-digit? "111")))
  (is (not (types/four-digit? "1984a")))
  (is (not (types/four-digit? "20o1")))
  (is (not (types/four-digit? "g999")))
  (is (not (types/string-year? "0oO0")))
  (is (not (types/string-year? "111")))
  (is (not (types/string-year? "1984a")))
  (is (not (types/string-year? "abcd")))
  (is (not (types/string-year? "999g")))
  (is (not (types/string-date? "a000-01-01")))
  (is (not (types/string-date? "a000-1-1")))
  (is (not (types/string-date? "a000-1-1")))
  (is (not (types/string-date? "0000-1-1")))
  (is (not (types/string-date? "1984a/4/15")))
  (is (not (types/string-date? "1984a/4/15")))
  (is (not (types/string-date? "999g.12.31")))
  (is (not (types/string-date? "1984415")))
  (is (not (types/string-date? "0000.:.1.:|:.1")))
  (is (not (types/string-date? "1984.:.4.:|:.15")))
  (is (not (types/string-date? "1984/4/15")))
  (is (not (types/string-bool? "t")))
  (is (not (types/string-bool? "T")))
  (is (not (types/string-bool? "f")))
  (is (not (types/string-bool? "F")))
  (is (not (types/string-bool? "1")))
  (is (not (types/string-bool? "0"))))

(deftest validate-StrInt-positive-test
  (is (= "1" (schema/validate types/StrInt "1")))
  (is (= "0" (schema/validate types/StrInt "0")))
  (is (= "10000000" (schema/validate types/StrInt "10000000")))
  (is (= "-1" (schema/validate types/StrInt "-1")))
  (is (= "-0" (schema/validate types/StrInt "-0")))
  (is (= "-10000000" (schema/validate types/StrInt "-10000000"))))

(deftest validate-StrInt-negative-test
  (is (thrown? RuntimeException (schema/validate types/StrInt "3.14159")))
  (is (thrown? RuntimeException (schema/validate types/StrInt "alice"))))

(deftest validate-StrDate-positive-test
  (is (= "0000-01-01" (schema/validate types/StrDate "0000-01-01")))
  (is (= "1984/04/15" (schema/validate types/StrDate "1984/04/15")))
  (is (= "9999.12.31" (schema/validate types/StrDate "9999.12.31")))
  (is (= "19840415" (schema/validate types/StrDate "19840415")))
  (is (= "1984.:.04.:|:.15" (schema/validate types/StrDate "1984.:.04.:|:.15"))))

(deftest validate-StrDate-negative-test
  (is (thrown? RuntimeException (schema/validate types/string-date? "a000-01-01")))
  (is (thrown? RuntimeException (schema/validate types/string-date? "a000-1-1")))
  (is (thrown? RuntimeException (schema/validate types/string-date? "a000-1-1")))
  (is (thrown? RuntimeException (schema/validate types/string-date? "0000-1-1")))
  (is (thrown? RuntimeException (schema/validate types/string-date? "1984a/4/15")))
  (is (thrown? RuntimeException (schema/validate types/string-date? "1984a/4/15")))
  (is (thrown? RuntimeException (schema/validate types/string-date? "999g.12.31")))
  (is (thrown? RuntimeException (schema/validate types/string-date? "1984415")))
  (is (thrown? RuntimeException (schema/validate types/string-date? "0000.:.1.:|:.1")))
  (is (thrown? RuntimeException (schema/validate types/string-date? "1984.:.4.:|:.15")))
  (is (thrown? RuntimeException (schema/validate types/string-date? "1984/4/15"))))
