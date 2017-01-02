package stores

import (
	"jvmgo/ch05/instructions/base"
	"jvmgo/ch05/rtda"
)

type FSTORE struct{ base.Index8Instruction }
type FSTORE_0 struct{ base.NoOperandInstruction }
type FSTORE_1 struct{ base.NoOperandInstruction }
type FSTORE_2 struct{ base.NoOperandInstruction }
type FSTORE_3 struct{ base.NoOperandInstruction }

func (self *FSTORE) Execute(frame *rtda.Frame) {
	_fstore(frame, uint(self.Index))
}

func (self *FSTORE_0) Execute(frame *rtda.Frame) {
	_fstore(frame, 0)
}

func (self *FSTORE_1) Execute(frame *rtda.Frame) {
	_fstore(frame, 1)
}

func (self *FSTORE_2) Execute(frame *rtda.Frame) {
	_fstore(frame, 2)
}

func (self *FSTORE_3) Execute(frame *rtda.Frame) {
	_fstore(frame, 3)
}

func _fstore(frame *rtda.Frame, index uint) {
	val := frame.OperandStack().PopFloat()
	frame.LocalVars().SetFloat(index, val)
}
