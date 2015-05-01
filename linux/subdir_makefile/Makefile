subdirs = call_pas pas_service uart_comm

.PHONY: all
all: $(subdirs)


.PHONY: $(subdirs)
$(subdirs):
	$(MAKE) -C $@

sub_clean = $(addsuffix .clean, $(subdirs))

.PHONY: $(sub_clean)
$(sub_clean):%.clean:
	$(MAKE) -C $* clean

# TODO: clean subdirs
clean: $(sub_clean)

