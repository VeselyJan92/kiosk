<template>

  <div class="popup-container" >

    <div class="popup-content">

      <div class="title">
        <h3>{{props.title}}</h3>
        <div @click.stop="close" class="close"> <span class="material-icons " >close</span>Zpět</div>

      </div>

      <slot></slot>


    </div>

  </div>


</template>

<script setup>



import {onMounted, onUnmounted} from "vue";
import router from "@/router";

const props = defineProps({title: String, route: Boolean})
const emit = defineEmits(["close"])

function close(){
  if (props.route){
    router.back()
  }else {
    emit('close')
  }

}


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



<style lang="scss" scoped>



.close{
  height: 50px;
  width: 150px;
  background-color: #c2c9d2;
  border-radius: 5px;
  float: right;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.popup-container{
  z-index: 1001;
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: center;

  top: 0;
  left: 0;

  width: 100vw;
  height: 100%;

  background-color: rgba(0, 0, 0, 0.4);
}



.popup-content{
  width: calc(min(600px, 100%) - 20px);
  max-height: calc(100% - 50px);
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

</style>

<style>
.popup-footer{
  display: flex;
  justify-content: flex-end;
  align-items: center;

  margin-top: 10px;


  gap: 10px;
}
</style>