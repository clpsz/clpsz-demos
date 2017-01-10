package comparisons

import (
	"jvmgo/ch05/instructions/base"
	"jvmgo/ch05/rtda"
)

type DCMPG struct{ base.NoOperandInstruction }
type DCMPL struct{ base.NoOperandInstruction }

func _dcmp(frame *rtda.Frame, gFlag bool) {
	stack := frame.OperandStack()
	v2 := stack.PopDouble()
	v1 := stack.PopDouble()

	if v1 > v2 {
		stack.PushInt(1)
	} else if v1 == v2 {
		stack.PushInt(0)
	} else if v1 < v2 {
		stack.PushInt(-1)
	} else {
		if gFlag {
			stack.PushInt(1)
		} else {
			stack.PushInt(-1)
		}
	}
}

func (self *DCMPG) Execute(frame *rtda.Frame) {
	_dcmp(frame, true)
}

func (self *DCMPL) Execute(frame *rtda.Frame) {
	_dcmp(frame, false)
}
