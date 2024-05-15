package pojo;

import java.util.ArrayList;

public class Pet {

    public long id;
    public Category category;
    public String name;
    public ArrayList<String> photoUrls;
    public ArrayList<Tag> tags;
    public String status;

    public Pet(
            long id,
            Category category,
            String name,
            ArrayList<String> photoUrls,
            ArrayList<Tag> tags,
            String status
    ) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public Pet() {
    }

    public static class Category {

        public long id;
        public String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (!(o instanceof Category category)) return false;
            return this.id == category.id && this.name.equals(category.name);
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }
    }

    public static class Tag {

        public long id;
        public String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (!(o instanceof Tag tag)) return false;
            return this.id == tag.id && this.name.equals(tag.name);
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(ArrayList<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Pet pet)) return false;
        return this.id == pet.id && category.equals(pet.category)
                && this.name.equals(pet.name) && this.photoUrls.equals(pet.photoUrls)
                && this.tags.equals(pet.tags) && this.status.equals(pet.status);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (photoUrls != null ? photoUrls.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
