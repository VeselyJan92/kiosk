<template>
    <div>
      <span v-if="props.icon" class="material-icons md-24">{{props.icon}}</span>
      <span>{{ props.text }}</span>
    </div>
</template>

<script setup>
const props = defineProps({
  icon: String,
  text: String,
})


</script>

<style scoped lang="scss">

div {
  padding: 10px;
  min-width: 180px;
  height: 50px;

  background-color: #F4F5F6;

  border: 1px solid #C4C4C4;
  border-radius: 8px;

  display: flex;
  align-items: center;

  span:not(.material-icons) {
   flex-grow: 1;
    text-align: center;
  }

  span{
    font-size: 14px;
  }
}



</style>