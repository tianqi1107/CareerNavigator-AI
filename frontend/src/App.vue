<template>
  <router-view v-slot="{ Component }">
    <transition name="fade" mode="out-in">
      <component :is="Component" />
    </transition>
  </router-view>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'

onMounted(() => {
  // Check dark mode preference
  if (localStorage.getItem('darkMode') === 'false') {
    document.documentElement.classList.remove('dark')
  }
})
</script>
