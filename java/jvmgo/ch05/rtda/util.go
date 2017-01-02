package rtda

import (
	"math"
)

func Double2Long(dVal float64) int64 {
	bits := uint64(math.Float64bits(dVal))
	return int64(bits)
}

func Long2Double(lVal int64) float64 {
	bits := uint64(lVal)
	return math.Float64frombits(bits)
}
