<template>

  <div class="trip-banner"  @click.stop="show">

    <img :src="data.main_img_url">

    <div>

      <div class="details">
          <div>
            <Tag class="tag" :data="{name: 'U Veselých', color: '#caeaaa'}"></Tag>
          </div>

        <span class="date">24. 01. 2022</span>

        <span class="material-icons" @click.stop="edit" v-if="user.edit_mode">edit</span>

      </div>

      <h4>{{data.title}}</h4>

      <p>{{ data.text.toString().replace(/(<([^>]+)>)/gi, "").slice(0, 150) }}</p>


      <div class="tags">
        <Tag v-for="item in data.tags" :data="{name: item, color: '#eef3e7'}"></Tag>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import Tag from "../travel/Tag.vue";
import {useUserStore} from "@/stores/user";
import {useRouter} from "vue-router";

const props = defineProps({data: Object})

const user = useUserStore()

const router = useRouter()
const params = {id: props.data.hotel_id, tripId: props.data._id}

const show = () => router.push({name: 'hotel-trip', params });
const edit = () => router.push({name: 'edit-hotel-trip', params });

</script>

<style scoped lang="scss">

.trip-banner {

  cursor: pointer;
  display: flex;
  flex-direction: column;

  min-width: 70vw;

  gap: 10px;

  @include md{
    min-width: unset;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border: 1px solid #E7ECF3;
    border-radius: 20px;
    background-color: #ececec;
    padding: 15px;
  }
  @include lg{
    flex-direction: row;
  }

  &:hover{
    background-color: #E7ECF3;
  }

  h4{
    margin: 0;
    font-size: 17px;
    font-weight: bold;


    @include md{
      font-weight: bold;
      font-size: 20px;

    }

  }

  p{
   margin-top: 3px;
    display: none;

    @include lg{
      display: block;
    }
  }


  img {

    aspect-ratio: 4/3;
    object-fit: cover;
    border-radius: 10px;
    width: 100%;

    @include lg{
      width: 40%;
    }


  }

  .tags {

    flex-direction: row;
    gap: 10px;
    display: none;

    @include lg{
      display: flex;
    }
  }

  .details{
    display: none;
    margin-bottom: 10px;
    gap: 20px;

    > div {
      flex-grow: 1;
    }

    @include lg{
      display: flex;
    }


    .material-icons{
      padding: 3px;
      border-radius: 2px;
    }
  }

}


</style>