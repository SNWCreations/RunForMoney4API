package snw.mods.rfm.api.util.property;

/**
 * Marks a domain of property keys. <br>
 * Each {@link PropertyBoard} belongs to exactly one domain, and only keys declared in that domain
 * can be read from or written to the board. This keeps unrelated key sets separated at compile time.
 * <p>
 * A domain may extend another domain to share keys with it: a board of the subdomain accepts the keys of
 * its superdomains, while a board of a superdomain rejects the keys of its subdomains.
 */
public interface PropertyDomain {
}
