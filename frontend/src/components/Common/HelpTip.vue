<template>
  <div>
    <slot
        data-tooltip-target="tooltip-right"
        data-tooltip-placement="right"
    ></slot>
    <div
        id="tooltip-right"
        role="tooltip"
        class="absolute z-10 invisible inline-block px-3 py-2 text-sm font-medium text-neutral-900 transition-opacity duration-300 bg-neutral-50 border border-neutral-100 rounded-lg shadow-sm opacity-0 tooltip"
    >{{tip}}<div class="tooltip-arrow" data-popper-arrow></div></div>
  </div>
</template>

<script>
import 'flowbite';
export default {
  name: "HelpTip",
  props: {
    tip: String
  }
}
</script>