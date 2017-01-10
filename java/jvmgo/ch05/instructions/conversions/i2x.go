package conversions

import (
	"jvmgo/ch05/instructions/base"
	"jvmgo/ch05/rtda"
)

type I2B struct{ base.NoOperandInstruction }
type I2C struct{ base.NoOperandInstruction }
type I2S struct{ base.NoOperandInstruction }
type I2L struct{ base.NoOperandInstruction }
type I2F struct{ base.NoOperandInstruction }
type I2D struct{ base.NoOperandInstruction }

func (self *I2B) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	ival := stack.PopInt()
	bval := int32(int8(ival))
	stack.PushInt(bval)
}

func (self *I2C) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	ival := stack.PopInt()
	cval := int32(uint16(ival))
	stack.PushInt(cval)
}

func (self *I2S) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	ival := stack.PopInt()
	sval := int32(int16(ival))
	stack.PushInt(sval)
}

func (self *I2L) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	ival := stack.PopInt()
	lval := int64(ival)
	stack.PushLong(lval)
}

func (self *I2F) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	ival := stack.PopInt()
	fval := float32(ival)
	stack.PushFloat(fval)
}

func (self *I2D) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	ival := stack.PopInt()
	dval := float64(ival)
	stack.PushDouble(dval)
}
