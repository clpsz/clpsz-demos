package comparisons

import (
	"jvmgo/ch05/instructions/base"
	"jvmgo/ch05/rtda"
)

type FCMPG struct{ base.NoOperandInstruction }
type FCMPL struct{ base.NoOperandInstruction }

func _fcmp(frame *rtda.Frame, gFlag bool) {
	stack := frame.OperandStack()
	v2 := stack.PopFloat()
	v1 := stack.PopFloat()

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

func (self *FCMPG) Execute(frame *rtda.Frame) {
	_fcmp(frame, true)
}

func (self *FCMPL) Execute(frame *rtda.Frame) {
	_fcmp(frame, false)
}
