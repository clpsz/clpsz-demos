package constants

import (
	"jvmgo/ch05/instructions/base"
	"jvmgo/ch05/rtda"
)

type ACONST_NULLL struct{ base.NoOperandInstruction }
type DCONST_0 struct{ base.NoOperandInstruction }
type DCONST_1 struct{ base.NoOperandInstruction }
type FCONST_0 struct{ base.NoOperandInstruction }
type FCONST_1 struct{ base.NoOperandInstruction }
type FCONST_2 struct{ base.NoOperandInstruction }
type ICONST_M1 struct{ base.NoOperandInstruction }
type ICONST_0 struct{ base.NoOperandInstruction }
type ICONST_1 struct{ base.NoOperandInstruction }
type ICONST_2 struct{ base.NoOperandInstruction }
type ICONST_3 struct{ base.NoOperandInstruction }
type ICONST_4 struct{ base.NoOperandInstruction }
type ICONST_5 struct{ base.NoOperandInstruction }
type LCONST_0 struct{ base.NoOperandInstruction }
type LCONST_1 struct{ base.NoOperandInstruction }

func (self *ACONST_NULLL) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushRef(nil)
}

func (self *DCONST_0) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushDouble(0.0)
}

func (self *DCONST_1) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushDouble(1.0)
}

func (self *FCONST_0) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushDouble(0.0)
}

func (self *FCONST_1) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushDouble(1.0)
}

func (self *FCONST_2) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushDouble(2.0)
}

func (self *ICONST_M1) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushInt(-1)
}

func (self *ICONST_0) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushInt(0)
}

func (self *ICONST_1) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushInt(1)
}

func (self *ICONST_2) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushInt(2)
}

func (self *ICONST_3) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushInt(3)
}

func (self *ICONST_4) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushInt(4)
}

func (self *ICONST_5) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushInt(5)
}

func (self *LCONST_0) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushInt(0)
}

func (self *LCONST_1) Execute(frame *rtda.Frame) {
	frame.OperandStack().PushInt(1)
}
