package conversions

import (
	"jvmgo/ch05/instructions/base"
	"jvmgo/ch05/rtda"
)

type F2I struct{ base.NoOperandInstruction }
type F2L struct{ base.NoOperandInstruction }
type F2D struct{ base.NoOperandInstruction }

func (self *F2I) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	fval := stack.PopFloat()
	ival := int32(fval)
	stack.PushInt(ival)
}

func (self *F2L) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	fval := stack.PopFloat()
	lval := int64(fval)
	stack.PushLong(lval)
}

func (self *F2D) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	fval := stack.PopFloat()
	dval := float64(fval)
	stack.PushDouble(dval)
}
