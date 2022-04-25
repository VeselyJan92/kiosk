<template>
  <nav>
    <div>

      <div class="logo">
        <img :src="hotel.data.logo_img_url">

        <div>
          <span>{{ hotel.data.hotel_name }}</span>
          <span>Informace pro Hosty</span>
        </div>
      </div>

      <div class="link">
        <router-link class="accommodation-link" to="/">Karta pobytu</router-link>
      </div>

      <div class="link">
        <span style="color: red">Prozkoumej</span>
        <router-link to="/">Oficiální stránky ubytovatele</router-link>
      </div>

      <div class="spacer"></div>

      <div class="contact">
        <div>
          <GrayButton icon="call" :text="hotel.data.contact_phone"></GrayButton>
        </div>

        <div>
          <GrayButton icon="email" :text="hotel.data.contact_email"></GrayButton>
        </div>
      </div>



    </div>


  </nav>
</template>

<script setup>
import GrayButton from "../GrayButton.vue";
import {useHotelStore} from "@/stores/hotel";


const hotel = useHotelStore()


</script>

<style scoped lang="scss">

nav{
  width: 100%;
  //background-color: #e2e2e2;
  background-color: white;

  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

  @include lg{
    height: 90px;
  }
}



nav > div {
  margin: 0 auto;
  padding: 15px;
  justify-content: space-between;
  display: flex;
  flex-direction: column;
  max-width: 1500px;
  height: 100%;
  gap: 10px;



  align-items: flex-start;


  @include md{
    gap: 20px;
  }

  @include lg{
    align-items: center;

    flex-direction: row;
  }

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
    display: none;

    @include lg{
      display: block;
      flex-grow: 1;
    }
  }

  &> div{
    height: 50px;

    display: flex;
    justify-content: flex-end;
    flex-direction: column;
  }

  .link{


    display: none;

    @include lg{
      display: flex;
      flex-direction: column;
    }

    a{
      text-decoration: none;
      color: black;

    }
  }



  a {
    font-size: 20px;
    line-height: 30px;
  }


  &> div:nth-child(2) a{
    font-weight: bold;
  }

}



.contact{
  gap: 20px;
  flex-direction: row;

  align-self: center;
  display: none;

  @include xl{
    display: flex;
  }

}

</style>