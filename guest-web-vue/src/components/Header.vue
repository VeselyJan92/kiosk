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


      <div>
        <NuxtLink to="/">Karta pobytu</NuxtLink>
      </div>

      <div>
        <span style="color: red">Prozkoumej</span>
        <NuxtLink to="/">Oficiální stránky ubytovatele</NuxtLink>

      </div>

      <div class="spacer"></div>


      <div>
        <GrayButton icon="call" text="+420 774 406 784"></GrayButton>
      </div>

      <div>
        <GrayButton icon="email" text="veselypenzion@email.cz"></GrayButton>
      </div>

      <div>
        <NuxtLink to="asdsa">
          <GrayButton icon="navigation" text="Navigace"></GrayButton>
        </NuxtLink>
      </div>

    </div>


  </nav>
</template>

<script>
import GrayButton from "./GrayButton.vue";
export default {
  name: "Header",
  components: {GrayButton}
}
</script>

<style scoped lang="scss">

nav{
  width: 100vw;
  height: 90px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}


nav > div {
  margin: 0 auto;
  justify-content: space-between;
  display: flex;
  flex-direction: row;
  max-width: 1920px;
  height: 100%;
  align-items: center;

  .logo {
    margin-right: 30px;
    display: flex;
    flex-direction: row;
    align-items: center;

    img {
      max-height: 70px;
    }

    > div{
      padding-left: 10px;
      display: flex;
      flex-direction: column;

      span:nth-child(1){
        font-weight: bold;
        font-size: 26px;
        line-height: 32px;
        padding-bottom: 5px;
      }

      span:nth-child(2){
        font-weight: bolder;
        font-size: 14px;
        line-height: 16px;
      }
    }


  }


  .spacer {
    flex-grow: 1;
  }

  &> div:not(.logo):not(.spacer){
    height: 50px;
    margin-left: 40px;
    display: flex;
    justify-content: flex-end;
    flex-direction: column;
  }

  a {
    font-size: 20px;
    line-height: 30px;
  }


  &> div:nth-child(2) a{
    font-weight: bold;
  }



}

</style>