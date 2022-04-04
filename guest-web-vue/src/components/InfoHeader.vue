<template>
  <div class="info-header">

<!--
    <div class="intro-img">
      <img src="/static/hotel.jpg">
      <div>7 dní do příjezdu</div>
    </div>
-->

    <div>
      <h1>Všechny informace o Vašem pobytu na jednom místě.</h1>
      <p v-if="false">Duis amet, ultricies dignissim magnis amet, nunc sit id ultrices congue eleifend felis condimentum gravida lorem morbi eget amet non id massa sapien aliquam etiam bibendum amet quam iaculis nascetur</p>
    </div>

  </div>
</template>

<script setup>



</script>

<style scoped lang="scss">
.info-header {
  margin-top: 56px;
  display: flex;
  flex-direction: row;
  gap: 20px;

  .intro-img {

    position: relative;

    img {
      border-top-left-radius: 15px;
      border-bottom-left-radius: 15px;

      width: 300px;
      aspect-ratio: 4/3;
    }

    div {
      color: red;
      border-radius: 15px;
      position: absolute;
      background-color: #F9F9F9;
      padding: 10px;

      bottom: 10px;
      left: 10px;

    }
  }

  > div{
    max-width: 800px;
  }

  h1{
    margin: 0;
    font-size: 32px;
    line-height: 70px;
  }


  p{
    margin: 0;

    font-size: 20px;
    font-weight: 400;

    line-height: 36px;
  }
}
</style>