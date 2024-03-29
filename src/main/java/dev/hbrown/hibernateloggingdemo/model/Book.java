package dev.hbrown.hibernateloggingdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;
  @Version
  @Column(name = "version")
  private int version;

  @Column
  private String title;

  @Column(name = "publishingdate")
  @Temporal(TemporalType.DATE)
  private Date publishingDate;

  @ManyToOne
  @JoinColumn(name = "publisherid")
  private Publisher publisher;

  @ManyToMany
  @JoinTable(name = "bookauthor", joinColumns = {@JoinColumn(name = "bookid", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "authorid", referencedColumnName = "id")})
  private Set<Author> authors = new HashSet<Author>();

  @OneToMany(mappedBy = "book")
  private Set<Review> review = new HashSet<>();

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Book)) {
      return false;
    }
    Book other = (Book) obj;
    if (id != null) {
      if (!id.equals(other.id)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public String toString() {
    String result = getClass().getSimpleName() + " ";
    if (title != null && !title.trim().isEmpty())
      result += "title: " + title;
    return result;
  }
}
