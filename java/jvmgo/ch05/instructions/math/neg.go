package math

import (
	"jvmgo/ch05/instructions/base"
	"jvmgo/ch05/rtda"
)

type INEG struct{ base.NoOperandInstruction }
type LNEG struct{ base.NoOperandInstruction }
type FNEG struct{ base.NoOperandInstruction }
type DNEG struct{ base.NoOperandInstruction }

func (self *INEG) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	val := stack.PopInt()
	result := -val
	stack.PushInt(result)
}

func (self *LNEG) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	val := stack.PopLong()
	result := -val
	stack.PushLong(result)
}

func (self *FNEG) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	val := stack.PopFloat()
	result := -val
	stack.PushFloat(result)
}

func (self *DNEG) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	val := stack.PopDouble()
	result := -val
	stack.PushDouble(result)
}
