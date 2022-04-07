<template>
<div class="blog-post-banner">

  <h4>{{ data.title }}</h4>

  <p>{{data.text.toString().replace(/(<([^>]+)>)/gi, "").slice(0, 150)}}</p>

</div>
</template>

<script setup>

const props = defineProps({data: Object})

</script>

<style scoped lang="scss">

.blog-post-banner {
  background: #ebebeb;
  margin: 5px;
  padding: 15px;
  border-radius: 10px;
  display: flex;
  flex-direction: column;

  &:hover{
    background-color: #E7ECF3;
    cursor: pointer;
  }

  @include md{
    background-color: #ececec;
    border: 1px solid #E7ECF3;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

  }

  img{
    border-radius: 15px;
  }

  h3{
    height: 50%;
  }

  p{
    margin: 0;
    overflow: clip;
  }
}



</style>