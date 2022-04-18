<template>

  <KioskHeader></KioskHeader>

  <div v-if="kiosk.data" class="kiosk">

    <KioskTextInfoBox class="accommodation-text" style="margin-top: 20px"></KioskTextInfoBox>

    <Title title="Nápady pro vaší dovolenou" style="margin-top: 20px"></Title>

    <div class="travel-info">
      <KioskBlogPostBanner v-for="item in kiosk.data.blog_posts" :data="item"></KioskBlogPostBanner>
    </div>

    <h2 class="title-categories">Vyberte si podle</h2>

    <div class="categories">
      <CategoryButton
          v-for="(item, index) in kiosk.data.trip_categories"
          :category="item"
          :selected="index === kiosk.state.selectedCategory"
          @click="kiosk.selectCategory(index)"
      ></CategoryButton>
    </div>

    <div class="trips">
      <KioskTripBanner  v-for="(item) in kiosk.getSelectedCategoryTrips()" :data="item"></KioskTripBanner>
    </div>

    <div class="footer"></div>

  </div>

</template>


<script setup>
import KioskTextInfoBox from "../components/kiosk/KioskTextInfoBox.vue";
import CategoryButton from "../components/travel/CategoryButton.vue";
import Title from "../components/Title.vue";
import {useHotelStore} from "@/stores/hotel";
import {onBeforeMount} from "vue";
import {useRoute} from "vue-router";
import KioskHeader from "@/components/kiosk/KioskHeader.vue";
import KioskTripBanner from "@/components/kiosk/KioskTripBanner.vue";
import KioskBlogPostBanner from "@/components/kiosk/KioskBlogPostBanner.vue";

const kiosk = useHotelStore()
const route = useRoute()

onBeforeMount(async () => {
  kiosk.setHotelId(route.params.id)
  await kiosk.reload_kiosk()
});

</script>

<style scoped lang="scss">


.kiosk{
  margin-left: 10px;
  margin-right: 10px;

  .travel-info {
    display: grid;
    grid-template-columns: 1fr 1fr;

    gap: 10px;
  }

  > h2 {
    margin-top: 30px;
  }

  .title-categories{
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

  .trips {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    gap: 20px;
    flex-direction: row;

  }

  .footer{
    margin-top: 100px;
  }
}

</style>