<template>

  <div class="popup-container" >

    <div class="popup-content">

      <div class="title">
        <h3>{{props.title}}</h3>
        <span class="material-icons close" @click="$emit('close')">close</span>
      </div>

      <slot></slot>


    </div>

  </div>


</template>

<script setup>

import {onMounted, onUnmounted} from "vue";

const props = defineProps({title: String})


onMounted(()=>{
  const x = Math.round(window.scrollY)

  document.body.style.position = 'fixed';
  document.body.style.top = `-${x}px`
})

onUnmounted(()=>{
  const scrollY = document.body.style.top;

  document.body.style.position = '';
  document.body.style.top = '';
  document.documentElement.style = "scroll-behavior: auto";

  window.scrollTo({
    top: parseInt(scrollY || '0') * -1,
    behavior: 'auto'
  });

})

</script>

<style lang="scss">

.close{
  float: right;
  cursor: pointer;
}

.popup-container{
  z-index: 1001;
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: flex-start;

  top: 0;
  left: 0;

  width: 100vw;
  height: 100vh;

  background-color: rgba(0, 0, 0, 0.4);
}



.popup-content{
  margin-top: 10px;
  width: calc(min(600px, 100%) - 20px);
  max-height: calc(100% - 75px);
  background-color: #F4F5F6;
  border-radius: 20px;
  padding: 25px;
  overflow-y: auto;
}

.title{
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.popup-footer{
  display: flex;
  justify-content: flex-end;
  align-items: center;

  margin-top: 10px;


  gap: 10px;
}



</style>