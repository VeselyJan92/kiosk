<template>
  <router-view></router-view>

  <div v-if="kiosk.data">

    <Header></Header>

    <div class="content-width" style="margin: 0 auto">
      <AdminSettingsPanel c style="margin-top: 25px" v-if="user.edit_mode"></AdminSettingsPanel>

      <div class="accommodation-content">
        <TextInfoBox class="accommodation-text" :text="kiosk.data.accommodation_text"></TextInfoBox>
        <img src="/static/hotel.jpg">
      </div>


      <div class="footer travel-content-container">

        <Title title="Nápady pro vaši dovolenou"></Title>

        <div class="blog-posts">
          <BlogPostBanner v-for="item in kiosk.data.travel_info" :data="item"></BlogPostBanner>
        </div>

        <div class="mobile-trips" style="margin-top: 20px">

          <h2>Nejhledanější</h2>

          <div class="trips scrollbar-hidden">
            <TripBanner v-for="(item) in kiosk.getSelectedCategoryTrips()" :data="item"></TripBanner>
          </div>

          <h2>Nejhledanější</h2>


          <div class="trips scrollbar-hidden">
            <TripBanner v-for="(item) in kiosk.getSelectedCategoryTrips()" :data="item"></TripBanner>
          </div>

          <h2>Nejhledanější</h2>

          <div class="trips scrollbar-hidden">
            <TripBanner v-for="(item) in kiosk.getSelectedCategoryTrips()" :data="item"></TripBanner>
          </div>

          <div class="categories">
            <CategoryButton
                v-for="(item, index) in kiosk.data.trip_categories"
                :category="item"
            ></CategoryButton>
          </div>


        </div>

        <div class="desktop-trips">
          <h2 class="title-categories">Vyberte si podle</h2>


          <div class="categories">
            <CategoryButton
                v-for="(item, index) in kiosk.data.trip_categories"
                :category="item"
                :selected="index === kiosk.state.selectedCategory"
                @click="kiosk.selectCategory(index)"
            ></CategoryButton>
          </div>

          <div class="trips scrollbar-hidden">
            <TripBanner v-for="(item) in kiosk.getSelectedCategoryTrips()" :data="item"></TripBanner>
          </div>
        </div>


      </div>

    </div>


  </div>


</template>


<script setup>
import Header from "../components/Mobile/MobileHeader.vue";
import TextInfoBox from "../components/Mobile/MobileTextInfoBox.vue";
import BlogPostBanner from "../components/Mobile/MobileTravelInfoBanner.vue";
import CategoryButton from "../components/travel/CategoryButton.vue";
import TripBanner from "../components/Mobile/MobileTripBanner.vue";
import Title from "../components/Title.vue";
import {useHotelStore} from "@/stores/hotel";
import {onBeforeMount} from "vue";
import AdminSettingsPanel from "@/components/admin/AdminSettingsPanel.vue";
import {useRoute} from "vue-router";
import {useUserStore} from "@/stores/user";

const kiosk = useHotelStore()



const user = useUserStore()
const route = useRoute()

console.log("MobileView")

</script>

<style scoped lang="scss">

.info-header {
  margin-bottom: 20px;
}


.accommodation-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  margin-bottom: 20px;
  height: 400px;


  @include xl {
    flex-direction: row;
  }

  > div{
    height: 100%;
  }



  img{
    display: none;
    max-width: 100%;
    max-height: 100%;

    filter: brightness(0.9) ;


    border-top-right-radius: 10px;
    border-bottom-right-radius: 10px;

    @include md{
      display: block;
    }

  }


}

.blog-posts {
  display: flex;
  overflow-x: scroll;
  max-width: 100%;
  flex-direction: row;
  flex-wrap: nowrap;
  gap: 10px;

  @include xl {
    overflow: unset;
    max-width: 1300px;
    gap: 20px;

    @include lg {
      padding: 0;
    }
  }


  > div {
    min-width: 70vw;


    @include md {
      min-width: unset;
    }
  }
}

> h2 {
  margin-top: 30px;
}


.travel-content-container {
  @include md {
    background-color: #f9f9f9;
    padding: 20px;
    border-radius: 10px
  }
}


.title-categories {
  margin-top: 10px;
  text-align: center;
}


.categories {
  justify-content: center;
  width: 100%;
  margin-top: 20px;
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
  flex-direction: row;
  flex-wrap: wrap;

  @include lg {
    overflow-x: unset;
  }

  div {
    flex: 1 0 auto;
  }
}


.mobile-trips {
  display: block;

  h2 {
    margin-top: 20px;
  }

  @include lg {
    display: none;

  }
}

.trips {

  display: flex;
  gap: 20px;
  flex-direction: row;

  overflow-x: scroll;

  @include lg {
    overflow-x: unset;
    width: auto;

    display: grid;
    grid-template-columns: 1fr 1fr;
  }

  > div {
    min-width: 60vw;

    @include lg {
      //min-width: 200px;
      min-width: 500px;
    }

  }


  .footer {
    margin-bottom: 300px;
  }

}

.desktop-trips {
  display: none;

  @include lg {
    display: block;
  }
}

.scrollbar-hidden::-webkit-scrollbar {
  display: none;
}

/* Hide scrollbar for IE, Edge add Firefox */
.scrollbar-hidden {
  -ms-overflow-style: none;
  scrollbar-width: none; /* Firefox */
}


</style>