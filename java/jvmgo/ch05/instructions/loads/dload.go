package loads

import (
	"jvmgo/ch05/instructions/base"
	"jvmgo/ch05/rtda"
)

type DLOAD struct {
	base.Index8Instruction
}

type DLOAD_0 struct{ base.NoOperandInstruction }
type DLOAD_1 struct{ base.NoOperandInstruction }
type DLOAD_2 struct{ base.NoOperandInstruction }
type DLOAD_3 struct{ base.NoOperandInstruction }

func _dload(frame *rtda.Frame, index uint) {
	val := frame.LocalVars().GetDouble(index)
	frame.OperandStack().PushDouble(val)
}

func (self *DLOAD) Execute(frame *rtda.Frame) {
	_dload(frame, uint(self.Index))
}

func (self *DLOAD_0) Execute(frame *rtda.Frame) {
	_dload(frame, 0)
}

func (self *DLOAD_1) Execute(frame *rtda.Frame) {
	_dload(frame, 1)
}

func (self *DLOAD_2) Execute(frame *rtda.Frame) {
	_dload(frame, 2)
}

func (self *DLOAD_3) Execute(frame *rtda.Frame) {
	_dload(frame, 3)
}
