package classpath

import (
	"os"
	"path/filepath"
	"strings"
)

func newWildcardEntry(path string) CompositeEntry {
	compositeEntry := []Entry{}
	baseDir := path[:len(path)-1]

	walkFn := func(path string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}
		if info.IsDir() && path != baseDir {
			return filepath.SkipDir
		}
		if strings.HasSuffix(path, ".jar") || strings.HasSuffix(path, ".JAR") {
			compositeEntry = append(compositeEntry, newEntry(path))
		}

		return nil
	}

	filepath.Walk(baseDir, walkFn)

	return compositeEntry
}
