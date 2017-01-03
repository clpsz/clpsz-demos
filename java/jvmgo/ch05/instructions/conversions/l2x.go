package conversions

import (
	"jvmgo/ch05/instructions/base"
	"jvmgo/ch05/rtda"
)

type L2I struct{ base.NoOperandInstruction }
type L2F struct{ base.NoOperandInstruction }
type L2D struct{ base.NoOperandInstruction }

func (self *L2I) name(frame *rtda.Frame) {
	stack := frame.OperandStack()
	lval := stack.PopLong()
	ival := int32(lval)
	stack.PushInt(ival)
}

func (self *L2F) name(frame *rtda.Frame) {
	stack := frame.OperandStack()
	lval := stack.PopLong()
	fval := float32(lval)
	stack.PushFloat(fval)
}

func (self *L2D) name(frame *rtda.Frame) {
	stack := frame.OperandStack()
	lval := stack.PopLong()
	dval := float64(lval)
	stack.PushDouble(dval)
}
