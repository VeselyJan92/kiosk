<template>
  <div class="text-container">

    <h4>Informace o ubytování</h4>

    <div>
      <p v-html="text"></p>
    </div>
  </div>




</template>

<script setup>
defineProps({text: String})

</script>

<style scoped lang="scss">

.text-container {
  width: 100%;

  border-bottom-left-radius: 10px;
  border-top-left-radius: 10px;

  @include md{
    background-color: whitesmoke;
    border: 1px solid #EAEAEA;
    padding: 10px
  }
  h4{
    height: 30px;
  }

  > div {
    max-height: calc(100% - 40px);
    overflow-y: scroll;
  }
}

p{
  font-size: 14px;
  line-height: 14px;
  margin-bottom: 0;

}

::-webkit-scrollbar {
width: 8px;
height: 10px;

}
::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background: #c2c9d2;
}

</style>