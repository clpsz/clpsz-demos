package classfile

type ConstantMemberInfo struct {
	cp               ConstantPool
	classIndex       uint16
	nameAndTypeIndex uint16
}

type ConstantFieldrefInfo struct {
	ConstantMemberInfo
}
type ConstantMethodrefInfo struct {
	ConstantMemberInfo
}
type ConstantInterfaceMethodrefInfo struct {
	ConstantMemberInfo
}

func (self *ConstantMemberInfo) readInfo(reader *ClassReader) {
	self.classIndex = reader.readUint16()
	self.nameAndTypeIndex = reader.readUint16()
}

func (self *ConstantMemberInfo) ClassName() string {
	return self.cp.getClassName(self.classIndex)
}

func (self *ConstantMemberInfo) NameAndDescriptor() (string, string) {
	return self.cp.getNameAndType(self.nameAndTypeIndex)
}
