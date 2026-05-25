<template>
  <div class="markdown-body" v-html="renderedContent"></div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { marked } from 'marked'

const props = defineProps<{ content: string }>()

marked.setOptions({
  breaks: true,
  gfm: true,
})

const renderedContent = computed(() => {
  if (!props.content) return ''
  return marked.parse(props.content) as string
})
</script>

<style scoped>
.markdown-body {
  line-height: 1.8;
  color: #374151;
  font-size: 14px;
}

.dark .markdown-body {
  color: #d1d5db;
}

.markdown-body :deep(h1) {
  font-size: 1.5em;
  font-weight: 700;
  margin: 1em 0 0.5em;
  color: #111827;
  border-bottom: 1px solid #e5e7eb;
  padding-bottom: 0.3em;
}

.dark .markdown-body :deep(h1) {
  color: #f9fafb;
  border-bottom-color: #374151;
}

.markdown-body :deep(h2) {
  font-size: 1.3em;
  font-weight: 600;
  margin: 0.8em 0 0.4em;
  color: #111827;
}

.dark .markdown-body :deep(h2) {
  color: #f3f4f6;
}

.markdown-body :deep(h3) {
  font-size: 1.1em;
  font-weight: 600;
  margin: 0.6em 0 0.3em;
  color: #1f2937;
}

.dark .markdown-body :deep(h3) {
  color: #e5e7eb;
}

.markdown-body :deep(p) {
  margin: 0.5em 0;
}

.markdown-body :deep(ul),
.markdown-body :deep(ol) {
  margin: 0.5em 0;
  padding-left: 1.5em;
}

.markdown-body :deep(li) {
  margin: 0.25em 0;
}

.markdown-body :deep(ul li) {
  list-style-type: disc;
}

.markdown-body :deep(ol li) {
  list-style-type: decimal;
}

.markdown-body :deep(strong) {
  font-weight: 600;
  color: #111827;
}

.dark .markdown-body :deep(strong) {
  color: #f9fafb;
}

.markdown-body :deep(em) {
  font-style: italic;
}

.markdown-body :deep(code) {
  background: #f3f4f6;
  padding: 0.15em 0.4em;
  border-radius: 4px;
  font-size: 0.9em;
  font-family: 'Courier New', monospace;
  color: #dc2626;
}

.dark .markdown-body :deep(code) {
  background: #1f2937;
  color: #f87171;
}

.markdown-body :deep(pre) {
  background: #1f2937;
  color: #e5e7eb;
  padding: 1em;
  border-radius: 8px;
  overflow-x: auto;
  margin: 0.8em 0;
}

.markdown-body :deep(pre code) {
  background: transparent;
  color: inherit;
  padding: 0;
}

.markdown-body :deep(blockquote) {
  border-left: 4px solid #3b82f6;
  padding-left: 1em;
  margin: 0.8em 0;
  color: #6b7280;
  background: #f9fafb;
  padding: 0.5em 1em;
  border-radius: 0 4px 4px 0;
}

.dark .markdown-body :deep(blockquote) {
  background: #1f2937;
  color: #9ca3af;
}

.markdown-body :deep(table) {
  border-collapse: collapse;
  width: 100%;
  margin: 0.8em 0;
}

.markdown-body :deep(th),
.markdown-body :deep(td) {
  border: 1px solid #e5e7eb;
  padding: 0.5em 0.8em;
  text-align: left;
}

.dark .markdown-body :deep(th),
.dark .markdown-body :deep(td) {
  border-color: #374151;
}

.markdown-body :deep(th) {
  background: #f3f4f6;
  font-weight: 600;
}

.dark .markdown-body :deep(th) {
  background: #1f2937;
}

.markdown-body :deep(tr:nth-child(even)) {
  background: #f9fafb;
}

.dark .markdown-body :deep(tr:nth-child(even)) {
  background: #111827;
}

.markdown-body :deep(hr) {
  border: none;
  border-top: 1px solid #e5e7eb;
  margin: 1em 0;
}

.dark .markdown-body :deep(hr) {
  border-top-color: #374151;
}

.markdown-body :deep(a) {
  color: #3b82f6;
  text-decoration: none;
}

.markdown-body :deep(a:hover) {
  text-decoration: underline;
}
</style>
