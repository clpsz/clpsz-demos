package conversions

import (
	"jvmgo/ch05/instructions/base"
	"jvmgo/ch05/rtda"
)

type D2F struct{ base.NoOperandInstruction }
type D2I struct{ base.NoOperandInstruction }
type D2L struct{ base.NoOperandInstruction }

func (self *D2F) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	dval := stack.PopDouble()
	fval := float32(dval)
	stack.PushFloat(fval)
}

func (self *D2I) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	dval := stack.PopDouble()
	ival := int32(dval)
	stack.PushInt(ival)
}

func (self *D2L) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	dval := stack.PopDouble()
	lval := int64(dval)
	stack.PushLong(lval)
}
