<template>
  <div class="title">
    <h1>{{ props.title }}</h1>
    <div class="hr"></div>
  </div>

</template>

<script setup>
const props = defineProps({ title: String })
</script>

<style scoped>
.title{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

h1{
  font-size: 42px;
  text-align: center;
}

.hr{
  background: #FA8F54;
  border-radius: 5px;
  height: 5px;
  width: 100px;
  border: none;
  margin-bottom: 15px;
  z-index: 1000;
}

</style>