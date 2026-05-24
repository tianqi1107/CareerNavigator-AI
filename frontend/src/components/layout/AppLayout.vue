<template>
  <div class="min-h-screen bg-gray-50 dark:bg-dark-bg">
    <AppHeader />
    <main class="pt-16">
      <router-view v-slot="{ Component }">
        <transition name="slide" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>
  </div>
</template>

<script setup lang="ts">
import AppHeader from './AppHeader.vue'
</script>
