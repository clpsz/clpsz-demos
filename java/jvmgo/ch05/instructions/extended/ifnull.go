package extended

import (
	"jvmgo/ch05/instructions/base"
	"jvmgo/ch05/rtda"
)

type IFNULL struct{ base.BranchInstruction }
type IFNONNULL struct{ base.BranchInstruction }

func (self *IFNULL) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	ref := stack.PopRef()
	if ref == nil {
		base.Branch(frame, self.Offset)
	}
}

func (self *IFNONNULL) Execute(frame *rtda.Frame) {
	stack := frame.OperandStack()
	ref := stack.PopRef()
	if ref != nil {
		base.Branch(frame, self.Offset)
	}
}
