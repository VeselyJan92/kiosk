<template>
  <nav>
    <div>

      <div class="logo">
        <img src="/static/logo_tmp.png">

        <div>
          <span>Penzion U Veselých</span>
          <span>Informace pro Hosty</span>
        </div>
      </div>


      <router-link to="/kiosk/624837e9e377490f7e93f3e5">Informační kiosek</router-link>
      <router-link to="/kiosk/624837e9e377490f7e93f3e5/web">Web penzionu</router-link>


    </div>


  </nav>
</template>

<script setup>


</script>

<style scoped lang="scss">

nav{
  width: 100%;

  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

  height: 90px;
}



nav > div {
  margin: 0 auto;
  padding: 15px;
  justify-content: space-between;
  display: flex;
  flex-direction: row;
  height: 100%;
  gap: 10px;

  .logo {
    margin-right: 30px;
    display: flex;
    flex-direction: row;
    align-items: center;

    img {
      max-height: 70px;
    }

    > div {
      padding-left: 10px;
      display: flex;
      flex-direction: column;

      span:nth-child(1) {
        font-weight: bold;
        font-size: 26px;
        line-height: 32px;
        padding-bottom: 5px;
      }

      span:nth-child(2) {
        font-weight: bolder;
        font-size: 14px;
        line-height: 16px;
      }
    }
  }


  .back{
    display: none;
    align-items: center;
    justify-content: center;
    height: 100%;
    width: 200px;
    background-color: #c2c9d2;

    span{
      font-size: 20px;
      font-weight: bold;

    };
  }
}


</style>